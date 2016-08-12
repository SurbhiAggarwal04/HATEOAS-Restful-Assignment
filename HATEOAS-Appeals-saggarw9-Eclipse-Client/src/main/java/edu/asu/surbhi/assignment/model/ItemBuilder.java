package edu.asu.surbhi.assignment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemBuilder {

	private List<Appeal> appeals;

	private String email;
	private AppealStatus status = AppealStatus.PREPARING;

	public static ItemBuilder itemBuilder() {
		return new ItemBuilder();
	}

	public Items build()
	{
		 Random r = new Random();
		Email email=Email.values()[r.nextInt(Email.values().length)];
		return new Items(appeals,email.name(),status);
	}
	public Items build(Items items)
	{
	
		return new Items(appeals,items.getEmail(),items.getStatus());
	}
	public ItemBuilder withRandomItems() {
		Random rand=new Random();
		 int numberOfItems = rand.nextInt((5 - 1) + 1) + 1;
		this.appeals = new ArrayList<Appeal>();
		for (int i = 0; i < numberOfItems; i++) {
			 Random r = new Random();
		    AppealContent content  = AppealContent.values()[r.nextInt(AppealContent.values().length)];
		    boolean found=false;
		    for(int j=0;j<appeals.size();j++)
		    {
		    	if(appeals.get(j).getContent().equals(content.name()))
		    	{
		    		found=true;
		    		break;
		    	}
		    }
		    if(!found)
		    {
			Appeal appeal=new Appeal();
			appeal.setContent(content.name());
			//appeal.setStatus(AppealStatus.PREPARING);
			appeal.setSubject("APPEAL");
			appeals.add(appeal);
		    }
		}
		return this;
	}

}
