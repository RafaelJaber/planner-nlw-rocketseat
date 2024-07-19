package com.rocketseat.planner.infra.dto.mappers;

import com.rocketseat.planner.domain.entities.Link;
import com.rocketseat.planner.infra.dto.response.LinkResponseDetailed;
import org.springframework.stereotype.Component;

@Component
public class LinkMapper {

    public LinkResponseDetailed toResponseDetailed(Link link) {
        return new LinkResponseDetailed(
                link.getId(),
                link.getTitle(),
                link.getUrl()
        );
    }
}
