package in.hocg.generator;


import in.hocg.generator.core.CodeGenerator;
import in.hocg.generator.core.DataSource;
import in.hocg.generator.core.Module;

/**
 * Created by hocgin on 2020/5/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class GeneratorMain {

    public static void main(String[] args) {
        CodeGenerator.generateByTables(DataSource.DEFAULT, Module.BMW2,
            "bmw_payment_app",
            "bmw_payment_platform",
            "bmw_request_platform_log",
            "bmw_payment_trade",
            "bmw_payment_record",
            "bmw_refund_record",
            "bmw_notify_app",
            "bmw_notify_app_log",
            "");
    }

}
