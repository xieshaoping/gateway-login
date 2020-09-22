package cn.seczone.ssdlc.gateway.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author XieShaoping
 * @date 2020/8/31
 * @description
 */
@Component
@Order(value = 2)
@Slf4j
public class MyWebFilter implements WebFilter {

    @Value("#{'${ignore-urls}'.split(',')}")
    private List<String> ignoreUrls;

    @Value("#{'${server.servlet.context-path}'}")
    private String contextPath;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        int num = contextPath.length();
        path = path.substring(num);
        boolean same = ignoreUrls.contains(path);

        AtomicReference<UserDetails> loginUserDetails = new AtomicReference<>();

        exchange.getSession().flatMap(webSession -> {
            log.info("websession: {}", webSession.getId());
            loginUserDetails.set(webSession.getAttribute(cn.seczone.ssdlc.gateway.core.constant.AuthConsts.SPRING_SECURITY_CONTEXT));
            return chain.filter(exchange);
        }).then(Mono.fromRunnable(() -> {
            log.info("this is a post filter");
        }));
        UserDetails userDetails = loginUserDetails.get();
        if (!same && userDetails == null) {
            path = "/401";
        }
        //不在忽略名单中，且已登录，会被转发
        if (!same && userDetails != null) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest authErrorReq = request.mutate().path(path).build();
        ServerWebExchange authErrorExchange = exchange.mutate().request(authErrorReq).build();
        return chain.filter(authErrorExchange);
    }

}
