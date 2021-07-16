package com.company.uniclip.dto;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PhotoFiles implements Serializable {
    private String kind;
    private String nextPageToken;
    private String incompleteSearch;
    private List<PFile> mediaItems;
}
