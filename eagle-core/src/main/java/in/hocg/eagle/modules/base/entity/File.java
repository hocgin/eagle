package in.hocg.eagle.modules.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件引用表
 * </p>
 *
 * @author hocgin
 * @since 2020-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_file")
public class File extends AbstractEntity<File> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 文件名
     */
    @TableField("filename")
    private String filename;
    /**
     * 链接地址
     */
    @TableField("file_url")
    private String fileUrl;
    /**
     * 业务ID
     */
    @TableField("rel_id")
    private Long relId;
    /**
     * 业务类型
     */
    @TableField("rel_type")
    private Integer relType;
    /**
     * 排序,默认:1000
     */
    @TableField("sort")
    private Integer sort;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
    /**
     * 创建人
     */
    @TableField("creator")
    private Long creator;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @RequiredArgsConstructor
    @Getter
    public enum RelType {
        Unknown(0),
        Product(1);
        private final Integer code;
    }
}
