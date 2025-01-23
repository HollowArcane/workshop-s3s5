package model.dto.ticket;

import java.math.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import model.Tables;
import model.tables.records.VLabelTicketRecord;

public class VLabelTicketDTO 
{
	private Integer id;
	private BigDecimal priceReparation;
	private Integer idCustomer;
	private String name;
	private String telephone;
	private String email;
	private String address;
	private Integer idModel;
	private String serialNumber;
	private Integer idModelCategory;
	private String modelCategory;
	private Integer idBrand;
	private String brand;
	private String description;
	private String diagnostic;
	private Integer idTicketState;
	private String ticketState;
	private Timestamp dateStart;
	private Timestamp dateEnd;
	
	public VLabelTicketDTO() 
	{
	}
	
	public VLabelTicketDTO(VLabelTicketRecord record) 
	{
		this.id = record.getId();
		this.priceReparation = record.getPriceReparation();
		this.idCustomer = record.getIdCustomer();
		this.name = record.getName();
		this.telephone = record.getTelephone();
		this.email = record.getEmail();
		this.address = record.getAddress();
		this.idModel = record.getIdModel();
		this.serialNumber = record.getSerialNumber();
		this.idModelCategory = record.getIdModelCategory();
		this.modelCategory = record.getModelCategory();
		this.idBrand = record.getIdBrand();
		this.brand = record.getBrand();
		this.description = record.getDescription();
		this.diagnostic = record.getDiagnostic();
		this.idTicketState = record.getIdTicketState();
		this.ticketState = record.getTicketState();
		if(record.getDateStart() != null)
		{ this.dateStart = Timestamp.valueOf(record.getDateStart()); }
		if(record.getDateEnd() != null)
		{ this.dateEnd = Timestamp.valueOf(record.getDateEnd()); }
	}
	
	public VLabelTicketRecord toRecord(DSLContext context)  
	{
		VLabelTicketRecord newRecord = null;
		if(id == null) 
		{
			newRecord = context.newRecord(Tables.V_LABEL_TICKET);
		}
		else 
		{
			newRecord = context.fetchOne(Tables.V_LABEL_TICKET, Tables.V_LABEL_TICKET.ID.eq(id));
		}
		newRecord.setId(getId());
		newRecord.setPriceReparation(getPriceReparation());
		newRecord.setIdCustomer(getIdCustomer());
		newRecord.setName(getName());
		newRecord.setTelephone(getTelephone());
		newRecord.setEmail(getEmail());
		newRecord.setAddress(getAddress());
		newRecord.setIdModel(getIdModel());
		newRecord.setSerialNumber(getSerialNumber());
		newRecord.setIdModelCategory(getIdModelCategory());
		newRecord.setModelCategory(getModelCategory());
		newRecord.setIdBrand(getIdBrand());
		newRecord.setBrand(getBrand());
		newRecord.setDescription(getDescription());
		newRecord.setDiagnostic(getDiagnostic());
		newRecord.setIdTicketState(getIdTicketState());
		newRecord.setTicketState(getTicketState());
		newRecord.setDateStart(getDateStart().toLocalDateTime());
		newRecord.setDateEnd(getDateEnd().toLocalDateTime());
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
	
	public void setName(String name)  
	{
		this.name = name;
	}
	
	public void setTelephone(String telephone)  
	{
		this.telephone = telephone;
	}
	
	public void setEmail(String email)  
	{
		this.email = email;
	}
	
	public void setAddress(String address)  
	{
		this.address = address;
	}
	
	public void setIdModel(Integer idModel)  
	{
		this.idModel = idModel;
	}
	
	public void setSerialNumber(String serialNumber)  
	{
		this.serialNumber = serialNumber;
	}
	
	public void setIdModelCategory(Integer idModelCategory)  
	{
		this.idModelCategory = idModelCategory;
	}
	
	public void setModelCategory(String modelCategory)  
	{
		this.modelCategory = modelCategory;
	}
	
	public void setIdBrand(Integer idBrand)  
	{
		this.idBrand = idBrand;
	}
	
	public void setBrand(String brand)  
	{
		this.brand = brand;
	}
	
	public void setDescription(String description)  
	{
		this.description = description;
	}
	
	public void setDiagnostic(String diagnostic)  
	{
		this.diagnostic = diagnostic;
	}
	
	public void setIdTicketState(Integer idTicketState)  
	{
		this.idTicketState = idTicketState;
	}
	
	public void setTicketState(String ticketState)  
	{
		this.ticketState = ticketState;
	}
	
	public void setDateStart(Timestamp dateStart)  
	{
		this.dateStart = dateStart;
	}
	
	public void setDateEnd(Timestamp dateEnd)  
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
	
	public String getName()  
	{
		return this.name;
	}
	
	public String getTelephone()  
	{
		return this.telephone;
	}
	
	public String getEmail()  
	{
		return this.email;
	}
	
	public String getAddress()  
	{
		return this.address;
	}
	
	public Integer getIdModel()  
	{
		return this.idModel;
	}
	
	public String getSerialNumber()  
	{
		return this.serialNumber;
	}
	
	public Integer getIdModelCategory()  
	{
		return this.idModelCategory;
	}
	
	public String getModelCategory()  
	{
		return this.modelCategory;
	}
	
	public Integer getIdBrand()  
	{
		return this.idBrand;
	}
	
	public String getBrand()  
	{
		return this.brand;
	}
	
	public String getDescription()  
	{
		return this.description;
	}
	
	public String getDiagnostic()  
	{
		return this.diagnostic;
	}
	
	public Integer getIdTicketState()  
	{
		return this.idTicketState;
	}
	
	public String getTicketState()  
	{
		return this.ticketState;
	}
	
	public Timestamp getDateStart()  
	{
		return this.dateStart;
	}
	
	public Timestamp getDateEnd()  
	{
		return this.dateEnd;
	}
	
}