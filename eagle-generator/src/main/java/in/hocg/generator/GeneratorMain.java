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
        CodeGenerator.generateByTables(DataSource.DEFAULT, Module.BMW,
            "bmw_payment_way_rule",
            "bmw_payment_way_rule_item",
            "");
    }

}
