package com.auto.supplier.commons.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class NullFieldsUtil {
  public NullFieldsUtil() {
  }

  public static String[] getNullPropertyNames(Object source) {
    BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> emptyNames = new HashSet();
    PropertyDescriptor[] var4 = pds;
    int var5 = pds.length;

    for(int var6 = 0; var6 < var5; ++var6) {
      PropertyDescriptor pd = var4[var6];
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }

    String[] result = new String[emptyNames.size()];
    return (String[])emptyNames.toArray(result);
  }
}

