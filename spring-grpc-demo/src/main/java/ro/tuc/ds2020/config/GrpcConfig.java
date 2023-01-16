package ro.tuc.ds2020.config;

import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import ro.tuc.ds2020.services.GrpcServiceImp;
import ro.tuc.ds2020.services.UserService;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GrpcConfig{
    
	private final UserService userService;

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
		log.info("Basic grpc authenticator...");
        return new BasicGrpcAuthenticationReader();
    }

    @Bean
	public GrpcServerConfigurer keepAliveServerConfigurer() {
		return serverBuilder -> {
			if (serverBuilder instanceof NettyServerBuilder) {
				((NettyServerBuilder) serverBuilder)
						.keepAliveTime(30, TimeUnit.HOURS)
						.keepAliveTimeout(5, TimeUnit.MINUTES)
						.addService(new GrpcServiceImp(userService))
						.permitKeepAliveWithoutCalls(true)
						.build();
			}
		};
	}

}
