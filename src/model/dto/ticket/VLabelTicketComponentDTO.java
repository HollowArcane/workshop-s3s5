package model.dto.ticket;

import java.math.*;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import model.Tables;
import model.tables.records.VLabelTicketComponentRecord;

public class VLabelTicketComponentDTO 
{
	private Integer id;
	private Integer idTicket;
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
	private String dateStart;
	private String dateEnd;
	private Integer idComponent;
	private String componentSerialNumber;
	private Integer idComponentCategory;
	private String componentCategory;
	private Integer idComponentModel;
	private String componentModel;
	private Integer idBrand2;
	private String brand2;
	private String description2;
	private BigDecimal quantity;
	private BigDecimal costTotal;
	
	public VLabelTicketComponentDTO() 
	{
	}
	
	public VLabelTicketComponentDTO(VLabelTicketComponentRecord record) 
	{
		this.id = record.getId();
		this.idTicket = record.getIdTicket();
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
		this.dateStart = String.valueOf(record.getDateStart());
		this.dateEnd = String.valueOf(record.getDateEnd());
		this.idComponent = record.getIdComponent();
		this.componentSerialNumber = record.getComponentSerialNumber();
		this.idComponentCategory = record.getIdComponentCategory();
		this.componentCategory = record.getComponentCategory();
		this.idComponentModel = record.getIdComponentModel();
		this.componentModel = record.getComponentModel();
		this.idBrand2 = record.getIdBrand2();
		this.brand2 = record.getBrand2();
		this.description2 = record.getDescription2();
		this.quantity = record.getQuantity();
		this.costTotal = record.getCostTotal();
	}
	
	public Integer getId()  
	{
		return this.id;
	}
	
	public Integer getIdTicket()  
	{
		return this.idTicket;
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
	
	public String getDateStart()  
	{
		return this.dateStart;
	}
	
	public String getDateEnd()  
	{
		return this.dateEnd;
	}
	
	public Integer getIdComponent()  
	{
		return this.idComponent;
	}
	
	public String getComponentSerialNumber()  
	{
		return this.componentSerialNumber;
	}
	
	public Integer getIdComponentCategory()  
	{
		return this.idComponentCategory;
	}
	
	public String getComponentCategory()  
	{
		return this.componentCategory;
	}
	
	public Integer getIdComponentModel()  
	{
		return this.idComponentModel;
	}
	
	public String getComponentModel()  
	{
		return this.componentModel;
	}
	
	public Integer getIdBrand2()  
	{
		return this.idBrand2;
	}
	
	public String getBrand2()  
	{
		return this.brand2;
	}
	
	public String getDescription2()  
	{
		return this.description2;
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