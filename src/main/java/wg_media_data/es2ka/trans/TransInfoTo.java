
package wg_media_data.es2ka.trans;


/**
 * 数据转换
 */
public interface TransInfoTo<F,T> {
    T transSource(F f );
}
