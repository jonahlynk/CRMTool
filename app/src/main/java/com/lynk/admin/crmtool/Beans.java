package com.lynk.admin.crmtool;

/**
 * Created by Admin on 12/23/2015.
 */
public class Beans
{

    public class NewProspectData
    {
        String businessName;
        String businessType;
        String ContactPerson;
        String CreatedBy;
        String CustLatlng;
        String CustomerType;
        String HaveOwnTruck;
        String IsDiffLoadingPoint;
        String IsLabourRequired;
        String IsTheyUseTruck;
        String landmark;
        String LoadingAddress;
        String Phone1;
        String Phone2;
        String ProspectCode;
        String Remarks;
        String TrucksBookPerWeek;
        String VehicleType;

        NewProspectData(String businessName,
                String businessType,
                String ContactPerson,
                String CreatedBy,
                String CustLatlng,
                String CustomerType,
                String HaveOwnTruck,
                String IsDiffLoadingPoint,
                String IsLabourRequired,
                String IsTheyUseTruck,
                String landmark,
                String LoadingAddress,
                String Phone1,
                String Phone2,
                String ProspectCode,
                String Remarks,
                String TrucksBookPerWeek,
                String VehicleType)
        {
            this.businessName = businessName;
            this.businessType = businessType;
            this.CreatedBy = CreatedBy;
            this.ContactPerson = ContactPerson;
            this.CustLatlng = CustLatlng;
            this.CustomerType = CustomerType;
            this.HaveOwnTruck = HaveOwnTruck;
            this.IsDiffLoadingPoint = IsDiffLoadingPoint;
            this.IsLabourRequired = IsLabourRequired;
            this.IsTheyUseTruck = IsTheyUseTruck;
            this.landmark = landmark;
            this.LoadingAddress = LoadingAddress;
            this.Phone1 = Phone1;
            this.Phone2 = Phone2;
            this.ProspectCode = ProspectCode;
            this.Remarks = Remarks;
            this.TrucksBookPerWeek = TrucksBookPerWeek;
            this.VehicleType = VehicleType;


        }

        public String getBusinessName()
        {
            return this.businessName;
        }

        public String getBusinessType()
        {
            return this.businessType;
        }

        public String getContactPerson()
        {
            return this.ContactPerson;
        }

        public String getCreatedBy()
        {
            return this.CreatedBy;
        }

        public String getCustLatlng()
        {
            return this.CustLatlng;
        }

        public String getCustomerType()
        {
            return this.CustomerType;
        }

        public String getHaveOwnTruck()
        {
            return this.HaveOwnTruck;
        }

        public String getIsDiffLoadingPoint()
        {
            return this.IsDiffLoadingPoint;
        }

        public String getIsLabourRequired()
        {
            return this.IsLabourRequired;
        }

        public String getIsTheyUseTruck()
        {
            return this.IsTheyUseTruck;
        }

        public String getLandmark()
        {
            return this.landmark;
        }

        public String getLoadingAddress()
        {
            return this.LoadingAddress;
        }

        public String getPhone1()
        {
            return this.Phone1;
        }

        public String getPhone2()
        {
            return this.Phone2;
        }

        public String getProspectCode()
        {
            return this.ProspectCode;
        }

        public String getRemarks()
        {
            return this.Remarks;
        }

        public String getTrucksBookPerWeek()
        {
            return this.TrucksBookPerWeek;
        }

        public String getVehicleType()
        {
            return this.VehicleType;
        }




    }


}
