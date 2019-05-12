package android.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new vn.edu.hcmut.linexo.DataBinderMapperImpl());
  }
}
