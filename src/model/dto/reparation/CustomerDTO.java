package model.dto.reparation;

import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Table;

import model.Tables;
import model.tables.records.CustomerRecord;

public class CustomerDTO {

    private Integer id;
    private String name;
    private String telephone;
    private String email;
    private String adress;

    public CustomerDTO() {}

    public CustomerDTO(CustomerRecord record) 
    {
        this.id = record.getId();
        this.name = record.getName();
        this.telephone = record.getTelephone();
        this.email = record.getEmail();
        this.adress = record.getAddress();
    }

    public CustomerRecord toRecord(DSLContext context)
    {
        CustomerRecord newRecord = id == null ?
                context.newRecord(Tables.CUSTOMER):
                context.fetchOne(Tables.CUSTOMER, Tables.CUSTOMER.ID.eq(id));
        
        newRecord.setName(getName());
        newRecord.setTelephone(getTelephone());
        newRecord.setEmail(getEmail());
        newRecord.setAddress(getAdress());
        return newRecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }


    public static Result<CustomerRecord> fetchByDate(DSLContext context, LocalDate date)
    {
        if(date==null)
        { return context.fetch(Tables.CUSTOMER);} 
        else 
        {
            return null;
        }
    }
    
}
