package com.baby7blog.modules.blog.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class ConvertBeanUtils<Dto, Do> {
    public ConvertBeanUtils() {
    }

    public static <Do> Do dtoToDo(Object dtoEntity, Class<Do> doClass) {
        if (dtoEntity == null) {
            return null;
        } else if (doClass == null) {
            return null;
        } else {
            try {
                Do newInstance = doClass.newInstance();
                BeanUtils.copyProperties(dtoEntity, newInstance);
                return newInstance;
            } catch (Exception var3) {
                return null;
            }
        }
    }

    public static <Dto> Dto doToDto(Object doEntity, Class<Dto> dtoClass) {
        if (doEntity == null) {
            return null;
        } else if (dtoClass == null) {
            return null;
        } else {
            try {
                Dto newInstance = dtoClass.newInstance();
                BeanUtils.copyProperties(doEntity, newInstance);
                return newInstance;
            } catch (Exception var3) {
                return null;
            }
        }
    }

    public static <Dto> List<Dto> listToDto(List list, Class<Dto> dtoClass) {
        if (list == null) {
            return null;
        } else if (dtoClass == null) {
            return null;
        } else {
            try {
                List<Dto> returnList = new ArrayList();
                Iterator var3 = list.iterator();

                while(var3.hasNext()) {
                    Object o = var3.next();
                    Dto newInstance = dtoClass.newInstance();
                    BeanUtils.copyProperties(o, newInstance);
                    returnList.add(newInstance);
                }

                return returnList;
            } catch (Exception var6) {
                return null;
            }
        }
    }
}
