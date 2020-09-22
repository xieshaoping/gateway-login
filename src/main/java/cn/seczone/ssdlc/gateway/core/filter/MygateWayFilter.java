package cn.seczone.ssdlc.gateway.core.filter;

import cn.seczone.ssdlc.gateway.core.constant.AuthConsts;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
@Slf4j
@Component
public class MygateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        AtomicReference<UserDetails> loginUserDetails = new AtomicReference<>();

        //exchange.getRequest().getHeaders().put("test", Arrays.asList(new String[]{"testtest"}));
          exchange.getSession().flatMap(webSession -> {
            log.info("websession: {}", webSession.getId());
              loginUserDetails.set(webSession.getAttribute(AuthConsts.SPRING_SECURITY_CONTEXT));
            return chain.filter(exchange);
        }).then(Mono.fromRunnable(() -> {
            log.info("this is a post filter");
        }));
       /* UserDetails loginUserDetails = exchange.getSession().block().getAttribute(cn.seczone.ssdlc.gateway.core.constant.AuthConsts.SPRING_SECURITY_CONTEXT);;
        if (loginUserDetails != null){
            //添加头
            exchange.getRequest().getHeaders().add("token",loginUserDetails.getUsername());
        }
        return chain.filter(exchange);*/

       /* ServerHttpRequest host = exchange.getRequest().mutate().header("token", "token2").build();
        //将现在的request 变成 change对象
        ServerWebExchange build = exchange.mutate().request(host).build();

        // logger.info("test port------->"+port);
        return chain.filter(build).then(Mono.fromRunnable(()->{
            log.info(" 后置 : " +exchange.getResponse().getStatusCode() + "\t"+ exchange.getRequest().getURI().toString());
        }));*/

        UserDetails userDetails = loginUserDetails.get();
        if (userDetails == null){
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpRequest authErrorReq = request.mutate().path("/401").build();
            ServerWebExchange authErrorExchange = exchange.mutate().request(authErrorReq).build();
            return chain.filter(authErrorExchange);
        }
        //向headers中放文件，记得build
         ServerHttpRequest request = exchange.getRequest().mutate().header("token", userDetails.getUsername()).build();
        //将现在的request 变成 exchange对象
        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     * @return 请求体
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();

        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
