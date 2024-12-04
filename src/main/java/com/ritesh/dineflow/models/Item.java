package com.ritesh.dineflow.models;

import com.ritesh.dineflow.enums.Tag;
import com.ritesh.dineflow.enums.TaxType;
import lombok.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

	private String name;

	private String description;

	private List<Tag> tags;

	private String mainMediaId;

	private List<String> mediaIds;

	private Price basePrice;

	private boolean enabled;

	private double sellCount;

	private Map<TaxType, Tax> taxes;

}
