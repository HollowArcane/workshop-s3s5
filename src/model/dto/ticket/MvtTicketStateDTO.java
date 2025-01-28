package model.dto.ticket;

import java.math.*;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import model.Tables;
import model.tables.records.MvtTicketStateRecord;

public class MvtTicketStateDTO 
{
	private Integer id;
	private LocalDateTime datetime;
	private Integer idTicket;
	private Integer idTicketState;
	
	public MvtTicketStateDTO() 
	{
	}
	
	public MvtTicketStateDTO(MvtTicketStateRecord record) 
	{
		this.id = record.getId();
		this.datetime = record.getDatetime();
		this.idTicket = record.getIdTicket();
		this.idTicketState = record.getIdTicketState();
	}
	
	public MvtTicketStateRecord toRecord(DSLContext context)  
	{
		MvtTicketStateRecord newRecord = null;
		if(id == null) 
		{
			newRecord = context.newRecord(Tables.MVT_TICKET_STATE);
		}
		else 
		{
			newRecord = context.fetchOne(Tables.MVT_TICKET_STATE, Tables.MVT_TICKET_STATE.ID.eq(id));
		}
		newRecord.setDatetime(getDatetime());
		newRecord.setIdTicket(getIdTicket());
		newRecord.setIdTicketState(getIdTicketState());
		return newRecord;
	}
	
	public void setId(Integer id)  
	{
		this.id = id;
	}
	
	public void setDatetime(LocalDateTime datetime)  
	{
		this.datetime = datetime;
	}
	
	public void setIdTicket(Integer idTicket)  
	{
		this.idTicket = idTicket;
	}
	
	public void setIdTicketState(Integer idTicketState)  
	{
		this.idTicketState = idTicketState;
	}
	
	public Integer getId()  
	{
		return this.id;
	}
	
	public LocalDateTime getDatetime()  
	{
		return this.datetime;
	}
	
	public Integer getIdTicket()  
	{
		return this.idTicket;
	}
	
	public Integer getIdTicketState()  
	{
		return this.idTicketState;
	}
	
}