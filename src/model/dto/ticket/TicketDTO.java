package model.dto.ticket;

import java.math.*;
import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;

import model.Tables;
import model.tables.records.ModelRecord;
import model.tables.records.TicketRecord;

public class TicketDTO 
{
	private Integer id;
	private BigDecimal priceReparation;
	private Integer idCustomer;
	private Integer idModel;
	private String diagnostic;
	private Integer idEngineer;
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
		this.idEngineer = record.getIdEngineer();
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
		newRecord.setIdEngineer(getIdEngineer());
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

	public void setIdEngineer(Integer idEngineer) {
		this.idEngineer = idEngineer;
	}

	public Integer getIdEngineer() {
		return idEngineer;
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

	
	public static List<VLabelTicketDTO> fetchByComponentCategory(DSLContext context, Integer idComponentCategory)
	{    
		var result = context.select(
				Tables.V_LABEL_TICKET,
				DSL.listAgg(Tables.V_LABEL_COMPONENT.COMPONENT_CATEGORY, ", ").withinGroupOrderBy(Tables.V_LABEL_COMPONENT.COMPONENT_CATEGORY)
			)
			.from(Tables.V_LABEL_TICKET)
			.leftJoin(Tables.TICKET_COMPONENT)
				.on(Tables.V_LABEL_TICKET.ID.eq(Tables.TICKET_COMPONENT.ID_TICKET))
			.join(Tables.V_LABEL_COMPONENT)
				.on(Tables.TICKET_COMPONENT.ID_COMPONENT.eq(Tables.V_LABEL_COMPONENT.ID));

		if( idComponentCategory != null )
		{ result.and(Tables.V_LABEL_COMPONENT.ID_COMPONENT_CATEGORY.eq(idComponentCategory));  }

		return result.groupBy(Tables.V_LABEL_TICKET)
				.fetch(VLabelTicketDTO::new);
	}

	
	public static List<VLabelTicketDTO> fetchByIdModelCategoryAndReparationDetail(DSLContext context, Integer idModelCategory, Integer idComponentCategory)
	{
		var result = context.select(
                Tables.V_LABEL_TICKET,
                DSL.listAgg(Tables.V_LABEL_COMPONENT.COMPONENT_CATEGORY, ", ").withinGroupOrderBy(Tables.V_LABEL_COMPONENT.COMPONENT_CATEGORY)
            )
            .from(Tables.V_LABEL_TICKET)
            .leftJoin(Tables.TICKET_COMPONENT)
                .on(Tables.V_LABEL_TICKET.ID.eq(Tables.TICKET_COMPONENT.ID_TICKET))
			.join(Tables.V_LABEL_COMPONENT)
				.on(Tables.TICKET_COMPONENT.ID_COMPONENT.eq(Tables.V_LABEL_COMPONENT.ID))
			.where(Tables.V_LABEL_TICKET.DATE_END.isNotNull());

		if (idModelCategory != null)
		{ result.and(Tables.V_LABEL_TICKET.ID_MODEL_CATEGORY.eq(idModelCategory)); }

        if (idComponentCategory != null)
		{ result.and(Tables.V_LABEL_COMPONENT.ID_COMPONENT_CATEGORY.eq(idComponentCategory)); }

        return result.groupBy(Tables.V_LABEL_TICKET)
        		.fetch(VLabelTicketDTO::new);
    }
}