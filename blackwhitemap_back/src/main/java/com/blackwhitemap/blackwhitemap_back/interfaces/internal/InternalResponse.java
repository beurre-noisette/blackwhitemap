package com.blackwhitemap.blackwhitemap_back.interfaces.internal;

public class InternalResponse {
    
    public record SimpleChefInfo(
            Long id,
            String name,
            String nickname
    ) {}
}