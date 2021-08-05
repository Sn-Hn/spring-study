package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    @DisplayName("싱글톤 방식의 주의점")
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService bean1 = ac.getBean(StatefulService.class);
        StatefulService bean2 = ac.getBean(StatefulService.class);

        // ThreadA : A사용자 10000원 주문
        int priceA = bean1.order("userA", 10000);
        // ThreadB : B사용자 20000원 주문
        int priceB = bean2.order("userB", 20000);

        //ThreadA : 사용자A가 주문 금액 조회
//        int priceA = bean1.getPrice();
        System.out.println("priceA = " + priceA);
        System.out.println("priceB = " + priceB);

        Assertions.assertThat(priceA).isEqualTo(10000);
        Assertions.assertThat(priceB).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}