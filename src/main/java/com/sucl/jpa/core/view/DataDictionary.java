package com.sucl.jpa.core.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author sucl
 * @date 2019/4/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDictionary {
    private String group;
    private Collection<Elem> data;
}
