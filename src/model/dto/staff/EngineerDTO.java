package model.dto.staff;

import static model.Tables.REPARATION_FEEDBACK;

import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;

import model.Tables;
import model.tables.records.EngineerRecord;
import model.tables.records.VEngineerCommissionRecord;

public class EngineerDTO {

    private Integer id;
    private String name;
    private String telephone;
    private String email;
    private String adress;

    public EngineerDTO() {}

    public EngineerDTO(EngineerRecord record) 
    {
        this.id = record.getId();
        this.name = record.getName();
        this.telephone = record.getTelephone();
        this.email = record.getEmail();
        this.adress = record.getAddress();
    }

    public EngineerRecord toRecord(DSLContext context)
    {
        EngineerRecord newRecord = id == null ?
                context.newRecord(Tables.ENGINEER):
                context.fetchOne(Tables.ENGINEER, Tables.ENGINEER.ID.eq(id));
        
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

    public static Result<VEngineerCommissionRecord> fetchByDate(DSLContext context, LocalDate dateMin, LocalDate dateMax)
    {
        return context
            .selectFrom(Tables.V_ENGINEER_COMMISSION).fetch();
    }
    
}

