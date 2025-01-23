package model.dto.staff;

import static model.Tables.GENDER;
import static model.Tables.REPARATION_FEEDBACK;
import static model.Tables.V_ENGINEER_COMMISSION;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;

import model.Tables;
import model.tables.records.EngineerRecord;
import model.tables.records.VEngineerCommissionRecord;

public class EngineerDTO {

    private Integer id;
    private String name;
    private String telephone;
    private String email;
    private String adress;
    private Integer idGender;

    
    public EngineerDTO() {}
    
    public EngineerDTO(EngineerRecord record) 
    {
        this.id = record.getId();
        this.name = record.getName();
        this.telephone = record.getTelephone();
        this.email = record.getEmail();
        this.adress = record.getAddress();
        this.idGender = record.getIdGender();
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
        newRecord.setIdGender(getIdGender());
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
    
    public Integer getIdGender() {
        return idGender;
    }

    public void setIdGender(Integer idGender) {
        this.idGender = idGender;
    }

    public static Result<VEngineerCommissionRecord> fetchByDate(DSLContext context, LocalDate dateMin, LocalDate dateMax)
    {
        var data = context
                    .select(
                        V_ENGINEER_COMMISSION.ID,
                        V_ENGINEER_COMMISSION.NAME,
                        V_ENGINEER_COMMISSION.TELEPHONE,
                        V_ENGINEER_COMMISSION.ADDRESS,
                        V_ENGINEER_COMMISSION.EMAIL,
                        V_ENGINEER_COMMISSION.GENDER,
                        DSL.max(V_ENGINEER_COMMISSION.DATE).as("date"),
                        DSL.sum(V_ENGINEER_COMMISSION.COMMISSION).as("commission")
                    )
                    .from(V_ENGINEER_COMMISSION)
                    .where("1=1");
        
        if(dateMin!=null)
        {
            data.and(Tables.V_ENGINEER_COMMISSION.DATE.greaterOrEqual(dateMin));
        }
        if(dateMax!=null)
        {
            data.and(Tables.V_ENGINEER_COMMISSION.DATE.lessThan(dateMax));
        }
        return data
            .groupBy(
                V_ENGINEER_COMMISSION.ID,
                V_ENGINEER_COMMISSION.NAME,
                V_ENGINEER_COMMISSION.TELEPHONE,
                V_ENGINEER_COMMISSION.ADDRESS,
                V_ENGINEER_COMMISSION.GENDER,
                V_ENGINEER_COMMISSION.EMAIL
            ).fetchInto(V_ENGINEER_COMMISSION);
    }

    public static List<GenderCommissionDTO> fetchCommissionByGender(DSLContext context, LocalDate dateMin, LocalDate dateMax)
    {
        var data = context
            .select(
                V_ENGINEER_COMMISSION.ID_GENDER,
                V_ENGINEER_COMMISSION.GENDER,
                DSL.sum(V_ENGINEER_COMMISSION.COMMISSION).as("commission")
            )
            .from(V_ENGINEER_COMMISSION)
            .where("1=1");
            
            if(dateMin!=null)
            {
                data.and(Tables.V_ENGINEER_COMMISSION.DATE.greaterOrEqual(dateMin));
            }
            if(dateMax!=null)
            {
                data.and(Tables.V_ENGINEER_COMMISSION.DATE.lessThan(dateMax));
            }
            return data
                .groupBy(
                    V_ENGINEER_COMMISSION.ID_GENDER,
                    V_ENGINEER_COMMISSION.GENDER
                ).fetch( record -> {
                    GenderCommissionDTO genderCommissionDTO = new GenderCommissionDTO();  
                    genderCommissionDTO.setIdGender(record.component1());
                    genderCommissionDTO.setGender(record.component2());
                    genderCommissionDTO.setCommission(record.component3());
                    return genderCommissionDTO;
                });
    }
    
}

