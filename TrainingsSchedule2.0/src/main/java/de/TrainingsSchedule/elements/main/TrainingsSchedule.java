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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainingsSchedule {

	@XmlElement
	private PlanTemplate planTemplate;
	@XmlElement
	private Plan plan;
	@XmlElement
	private GoalList goalList;
	
	public TrainingsSchedule(PlanTemplate planTemplate) {
		this.planTemplate = planTemplate;
	}

}