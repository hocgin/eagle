package in.hocg.web;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbstractEntity<T extends AbstractEntity<?>> extends Model<T> {

    @Override
    public Serializable pkVal() {
        return super.pkVal();
    }
}
