package com.mresearch.databank.client.views;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;

public class ResearchLink extends Anchor{
	private Long id;
	public ResearchLink(long research_id,String text)
	{
		this.setText(text);
		this.id = research_id;
		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DatabankApp.get().getEventBus().fireEvent(new ShowResearchDetailsEvent(id));
			}
		});
	}
}
