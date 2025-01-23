package model.dto.ticket;

import java.math.*;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import model.Tables;
import model.tables.records.TicketRecord;

public class TicketDTO 
{
	private Integer id;
	private BigDecimal priceReparation;
	private Integer idCustomer;
	private Integer idModel;
	private String diagnostic;
	private Integer idTicketState;
	private LocalDateTime dateStart;
	private LocalDateTime dateEnd;
	
	public TicketDTO() 
	{
	}
	
	public TicketDTO(TicketRecord record) 
	{
		this.id = record.getId();
		this.priceReparation = record.getPriceReparation();
		this.idCustomer = record.getIdCustomer();
		this.idModel = record.getIdModel();
		this.diagnostic = record.getDiagnostic();
		this.idTicketState = record.getIdTicketState();
		this.dateStart = record.getDateStart();
		this.dateEnd = record.getDateEnd();
	}
	
	public TicketRecord toRecord(DSLContext context)  
	{
		TicketRecord newRecord = null;
		if(id == null) 
		{
			newRecord = context.newRecord(Tables.TICKET);
		}
		else 
		{
			newRecord = context.fetchOne(Tables.TICKET, Tables.TICKET.ID.eq(id));
		}
		newRecord.setPriceReparation(getPriceReparation());
		newRecord.setIdCustomer(getIdCustomer());
		newRecord.setIdModel(getIdModel());
		newRecord.setDiagnostic(getDiagnostic());
		newRecord.setDateStart(getDateStart());
		return newRecord;
	}
	
	public void setId(Integer id)  
	{
		this.id = id;
	}
	
	public void setPriceReparation(BigDecimal priceReparation)  
	{
		this.priceReparation = priceReparation;
	}
	
	public void setIdCustomer(Integer idCustomer)  
	{
		this.idCustomer = idCustomer;
	}
	
	public void setIdModel(Integer idModel)  
	{
		this.idModel = idModel;
	}
	
	public void setDiagnostic(String diagnostic)  
	{
		this.diagnostic = diagnostic;
	}
	
	public void setIdTicketState(Integer idTicketState)  
	{
		this.idTicketState = idTicketState;
	}
	
	public void setDateStart(LocalDateTime dateStart)  
	{
		this.dateStart = dateStart;
	}
	
	public void setDateEnd(LocalDateTime dateEnd)  
	{
		this.dateEnd = dateEnd;
	}
	
	public Integer getId()  
	{
		return this.id;
	}
	
	public BigDecimal getPriceReparation()  
	{
		return this.priceReparation;
	}
	
	public Integer getIdCustomer()  
	{
		return this.idCustomer;
	}
	
	public Integer getIdModel()  
	{
		return this.idModel;
	}
	
	public String getDiagnostic()  
	{
		return this.diagnostic;
	}
	
	public Integer getIdTicketState()  
	{
		return this.idTicketState;
	}
	
	public LocalDateTime getDateStart()  
	{
		return this.dateStart;
	}
	
	public LocalDateTime getDateEnd()  
	{
		return this.dateEnd;
	}
	
}