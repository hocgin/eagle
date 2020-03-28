package in.hocg.eagle.modules.ums.pojo.vo.authority;

import in.hocg.eagle.basic.datastruct.tree.AbstractTreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AuthorityTreeNodeVo extends AbstractTreeNode<AuthorityTreeNodeVo>
    implements Serializable {

    @ApiModelProperty("code")
    private String authorityCode;

}
