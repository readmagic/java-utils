package fun.oop.framework.javabean;



public interface BeanConvert<T,S> {
     T convertFrom(S input);
}
