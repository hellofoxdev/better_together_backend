package com.sebastianfox.food.repository.Label;

import com.sebastianfox.food.models.LabelText;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository extends CrudRepository<LabelText, Long> {
}
