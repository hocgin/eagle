package in.hocg.eagle.modules.ums.pojo.vo.authority;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.Tree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class AuthorityTreeNodeVo
        implements Serializable, Tree.Node<AuthorityTreeNodeVo> {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("code")
    private String authorityCode;
    @ApiModelProperty("父ID")
    private Long parentId;

    @ApiModelProperty("树节点")
    private List<AuthorityTreeNodeVo> children = Lists.newArrayList();

    @Override
    public Long getParentId() {
        return this.parentId;
    }

    @Override
    public void setChildren(List<AuthorityTreeNodeVo> children) {
        this.children = children;
    }
}
