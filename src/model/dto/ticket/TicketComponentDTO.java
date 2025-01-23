package model.dto.ticket;

import java.math.*;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import model.Tables;
import model.tables.records.TicketComponentRecord;

public class TicketComponentDTO 
{
	private Integer id;
	private Integer idTicket;
	private Integer idComponent;
	private BigDecimal quantity;
	private BigDecimal costTotal;
	
	public TicketComponentDTO() 
	{
	}
	
	public TicketComponentDTO(TicketComponentRecord record) 
	{
		this.id = record.getId();
		this.idTicket = record.getIdTicket();
		this.idComponent = record.getIdComponent();
		this.quantity = record.getQuantity();
		this.costTotal = record.getCostTotal();
	}
	
	public TicketComponentRecord toRecord(DSLContext context)  
	{
		TicketComponentRecord newRecord = null;
		if(id == null) 
		{
			newRecord = context.newRecord(Tables.TICKET_COMPONENT);
		}
		else 
		{
			newRecord = context.fetchOne(Tables.TICKET_COMPONENT, Tables.TICKET_COMPONENT.ID.eq(id));
		}
		newRecord.setId(getId());
		newRecord.setIdTicket(getIdTicket());
		newRecord.setIdComponent(getIdComponent());
		newRecord.setQuantity(getQuantity());
		newRecord.setCostTotal(getCostTotal());
		return newRecord;
	}
	
	public void setId(Integer id)  
	{
		this.id = id;
	}
	
	public void setIdTicket(Integer idTicket)  
	{
		this.idTicket = idTicket;
	}
	
	public void setIdComponent(Integer idComponent)  
	{
		this.idComponent = idComponent;
	}
	
	public void setQuantity(BigDecimal quantity)  
	{
		this.quantity = quantity;
	}
	
	public void setCostTotal(BigDecimal costTotal)  
	{
		this.costTotal = costTotal;
	}
	
	public Integer getId()  
	{
		return this.id;
	}
	
	public Integer getIdTicket()  
	{
		return this.idTicket;
	}
	
	public Integer getIdComponent()  
	{
		return this.idComponent;
	}
	
	public BigDecimal getQuantity()  
	{
		return this.quantity;
	}
	
	public BigDecimal getCostTotal()  
	{
		return this.costTotal;
	}
	
}