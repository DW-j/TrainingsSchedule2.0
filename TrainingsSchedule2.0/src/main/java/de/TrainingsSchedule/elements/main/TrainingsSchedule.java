package de.TrainingsSchedule.elements.main;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.TrainingsSchedule.elements.goals.GoalList;
import de.TrainingsSchedule.elements.specifics.Plan;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrainingsSchedule {

	@Setter
	@XmlElement
	private PlanTemplate planTemplate;
	@XmlElement
	private Plan plan;
	@Setter
	private GoalList goalList;

}