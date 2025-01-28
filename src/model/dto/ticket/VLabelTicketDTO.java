package model.dto.ticket;

import java.math.*;

import org.jooq.Record2;

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
	private Integer idEngineer;
	private String engineer;
	private String brand;
	private String description;
	private String diagnostic;
	private Integer idTicketState;
	private String ticketState;
	private String dateStart;
	private String dateEnd;
	private String components;

	public VLabelTicketDTO(Record2<VLabelTicketRecord, String> record) 
	{
		this(record.component1());
		this.components = record.component2();
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
		this.idEngineer = record.getIdEngineer();
		this.ticketState = record.getTicketState();
		this.engineer = record.getEngineer();
		this.dateStart = String.valueOf(record.getDateStart());
		this.dateEnd = String.valueOf(record.getDateEnd());
		this.components = "";
	}
	
	public Integer getIdEngineer() {
		return idEngineer;
	}

	public void setIdEngineer(Integer idEngineer) {
		this.idEngineer = idEngineer;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
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
	
	public String getDateStart()  
	{
		return this.dateStart;
	}
	
	public String getDateEnd()  
	{
		return this.dateEnd;
	}

	public String getComponents()
	{
		return components;
	}
}