package in.hocg.eagle.basic.aspect.named;

/**
 * Created by hocgin on 2020/2/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public enum NamedType {
    DataDict {
        @Override
        String getMethod() {
            return "selectOneByDataDict";
        }
    };
    
    /**
     * Object methodName(idForFieldName!String, args!String[])
     *
     * @return
     */
    abstract String getMethod();
}
