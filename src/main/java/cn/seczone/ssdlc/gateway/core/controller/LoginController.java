package cn.seczone.ssdlc.gateway.core.controller;

import cn.seczone.ssdlc.gateway.core.common.Exception.BadCredentialsException;
import cn.seczone.ssdlc.gateway.core.common.Exception.VerifyException;
import cn.seczone.ssdlc.gateway.core.common.constant.GlobalTipMsg;
import cn.seczone.ssdlc.gateway.core.common.response.JsonResult;
import cn.seczone.ssdlc.gateway.core.common.tools.Utils;
import cn.seczone.ssdlc.gateway.core.constant.AuthConsts;
import cn.seczone.ssdlc.gateway.core.entity.UserAuthMsg;
import cn.seczone.ssdlc.gateway.core.service.SUserService;
import cn.seczone.ssdlc.gateway.core.entity.vo.UserVo;
import cn.seczone.ssdlc.gateway.core.service.UserService;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * @author XieShaoping
 * @date 2020/8/28
 * @description
 */
@RestController
class LoginController {


    public static final String WINDOWS_GIT_URL = "d:/ssdlc/git/file/";

    public static final String LINUX_GIT_URL = "/opt/ssdlc/git/file/";

    public static final String JENKINS_FILE_NAME = "Jenkinsfile";

    @Autowired
    SUserService suserService;

    @Autowired
    UserService userService;

    private final Random random = new Random();

    @PostMapping("login")
    public JsonResult test(@RequestBody UserVo userVo, WebSession session) {
        UserDetails userAuthMsg;
        try {
            userAuthMsg = suserService.findUserByEmail(userVo, session);
        } catch (VerifyException e) {
            return JsonResult.createByFormValid(e.getMessage(), 1);
        } catch (BadCredentialsException e) {
            if (userVo.getUsername() != null) {
                userService.updateLoginCount(userVo.getUsername());
            }
            int code = userService.getUserLoginCount(userVo.getUsername());
            return JsonResult.createByFormValid(e.getMessage(), code);
        }
        return JsonResult.createBySuccess(userAuthMsg);
    }

    /*@PostMapping("test")
    public Object test(ServerWebExchange exchange){
        //suserService.findUserByEmail(userName);
        exchange.getSession().block().getAttributes().put("s","ds");
        return "test";
    }*/

    @RequestMapping("test")
    Object get(WebSession session) {
        return session.getAttribute(AuthConsts.SPRING_SECURITY_CONTEXT);
    }

    @RequestMapping("401")
    public JsonResult sessionTimeOut() {
        return JsonResult.createByNeedLogin(GlobalTipMsg.LOGIN_AGAIN);
    }

    /**
     * @param checkCode 输入验证码
     * @author : xiexiaofei
     * @date : Created in 2020/3/2
     * @description : 判断登录验证码
     */
    @GetMapping(path = "/verifyCheckCode")
    public JsonResult<String> verifyCheckCode(WebSession session,
                                              @RequestParam(value = "checkCode", required = false) String checkCode,
                                              @RequestParam(value = "userName", required = false) String userName) {
        Object checkCodes = session.getAttribute("checkCode");
        if (checkCodes == null) {
            return JsonResult.createByErrorMessage("验证码生成失败");
        }
        if (Utils.isEmpty(checkCode)) {
            return JsonResult.createByErrorMessage("验证不能为空");
        }
        if (!checkCodes.equals(checkCode.toUpperCase())) {
            return JsonResult.createByErrorMessage("验证失败");
        }
        //错误登录计数清零
        userService.clearLoginCountByUserName(userName);
        return JsonResult.createBySuccessMessage("验证成功");
    }

    /**
     * @author : xiexiaofei
     * @date : Created in 2020/3/2
     * @description :验证码图片生成
     */
    @GetMapping(path = "/checkCode")
    public Mono<Void> checkCode(WebSession session, ServerWebExchange exchange) throws IOException {
        //内存图片对象(TYPE_INT_BGR 选择图片模式RGB模式)
        BufferedImage image = new BufferedImage(90, 30, BufferedImage.TYPE_INT_BGR);
        //得到画笔
        Graphics graphics = image.getGraphics();
        //画之前要设置颜色，设置画笔颜色
        graphics.setColor(new Color(191, 210, 255));
        //填充矩形区域（指定要画的区域设置区）
        graphics.fillRect(0, 0, 90, 30);
        //为了防止黑客软件通过扫描软件识别验证码。要在验证码图片上加干扰线
        //给两个点连一条线graphics.drawLine();
        for (int i = 0; i < 5; i++) {
            //颜色也要随机（设置每条线随机颜色）
            graphics.setColor(getRandomColor());
            int x1 = random.nextInt(90);
            int y1 = random.nextInt(30);
            int x2 = random.nextInt(90);
            int y2 = random.nextInt(30);
            graphics.drawLine(x1, y1, x2, y2);
        }
        //拼接4个验证码，画到图片上
        char[] arrays = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            //设置字符的颜色
            int index = random.nextInt(arrays.length);
            builder.append(arrays[index]);
        }
        //创建session对象将生成的验证码字符串以名字为checkCode保存在session中
        session.getAttributes().put("checkCode", builder.toString());
        //将4个字符画到图片上graphics.drawString(str ,x,y);一个字符一个字符画
        for (int i = 0; i < builder.toString().length(); i++) {
            //颜色随机
            //graphics.setColor(getRandomColor());
            graphics.setColor(Color.blue);
            char item = builder.toString().charAt(i);
            graphics.drawString(item + "", 10 + (i * 20), 15);
        }
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) (exchange.getResponse());

        String path = getPathByOs();
        File file = new File(path);

        ImageIO.write(image, "png", file);
        byte[] bytes = getImageStream(file);
        Mono<Void> mono = exchange.getResponse().writeWith(Flux.create(sink -> {
            NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
            try {
                DataBuffer dataBuffer = nettyDataBufferFactory.wrap(bytes);
                sink.next(dataBuffer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            sink.complete();
        }));
        file.delete();
        return mono;
        //输出内存图片到输出流
    }

    public static byte[] getImageStream(File file) {
        byte[] buffer = null;

        FileInputStream fis;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            if(file.exists()) {
                file.delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    private Color getRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    /**
     * @author XieShaoping
     * @description 根据当前部署的系统，确定路径
     * @date 2020/7/23
     */
    public static String getPathByOs() throws IOException {
        String os = System.getProperty("os.name");
        String path;
        if (os.toLowerCase().startsWith("windows")) {
            path = WINDOWS_GIT_URL;
        } else {
            path = LINUX_GIT_URL;
        }
        path += Utils.getUUID() + ".png";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return path;
    }

    @RequestMapping("logout")
    public JsonResult logout(WebSession session){
        session.getAttributes().put(AuthConsts.SPRING_SECURITY_CONTEXT,"");
        return JsonResult.createBySuccessMessage("logout success");
    }

}
