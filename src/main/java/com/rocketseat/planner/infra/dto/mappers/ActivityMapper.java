package com.rocketseat.planner.infra.dto.mappers;

import com.rocketseat.planner.domain.entities.Activity;
import com.rocketseat.planner.infra.dto.response.ActivityResponseDetailed;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityResponseDetailed toResponseDetailed(Activity activity) {
        return new ActivityResponseDetailed(
                activity.getId(),
                activity.getTitle(),
                activity.getOccursAt()
        );
    }
}
