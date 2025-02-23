package com.calderon.denv.pep.mapper;

import com.calderon.denv.pep.dto.app.PlanResponse;
import com.calderon.denv.pep.model.app.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlanMapper {
  PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);

  @Mapping(target = "id", source = "id")
  PlanResponse toResponse(Plan plan);

  List<PlanResponse> toResponse(List<Plan> plan);
}
