package mx.com.villavicencio.system.puestos.factory;

import mx.com.villavicencio.system.puestos.dto.DtoPost;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class PostFactory {

    public static DtoPost newInstance(){
        return new DtoPost();
    }
    
    public static DtoPost newInstance(Integer id){
        DtoPost post = newInstance();
        post.setPostId(id);
        return post;
    }
    
    public static final DtoPost newInstance(String id){
        if(!StringUtils.isReallyEmptyOrNull(id)){
            return newInstance(NumberUtils.stringToNumber(id));
        }else{
            return null;
        }
    }
}
