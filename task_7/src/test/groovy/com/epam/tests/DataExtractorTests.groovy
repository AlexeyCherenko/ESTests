package com.epam.tests

import com.epam.elasticsearchDataExtract.DataExtractor
import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class DataExtractorTests {

    def responseObj = DataExtractor.extractDataFromES()

    @DataProvider(name = "Data for extracted values")
    Object [][] dataForExtractedValues(){
        def array = new Object[10][]
        array[0] = ['FirstName',['ELVA', 'JENNEFER', 'LAUREN', 'VANIA', 'GENARO', 'CIERRA', 'RUSS', 'JED', 'VENICE', 'SANDIE']]
        array[1] = ['LastName',['RECHKEMMER', 'WENIG', 'RIDENS', 'HAMMING', 'HARDNETT', 'BRIGGERMAN', 'LISHMAN', 'CROUT', 'LEDAY', 'CAVALIERI']]
        array[2] = ['Designation',['CEO', 'President', 'President', 'President', 'Vice President', 'Vice President', 'Vice President', 'Vice President', 'Vice President', 'Vice President']]
        array[3] = ['Salary',['154000', '110000', '123000', '127000', '108000', '106000', '109000', '108000', '105000', '108000']]
        array[4] = ['DateOfJoining',['1993-01-11', '2013-02-07', '2010-01-14', '1970-01-05', '2008-01-14', '1991-09-16', '1985-02-12', '1994-09-12', '2016-02-03', '2009-01-07']]
        array[5] = ['Address',['8417 Blue Spring St. Port Orange, FL 32127',
                               '16 Manor Station Court Huntsville, AL 35803',
                               '287 SE. Schoolhouse Street Clifton, NJ 07011',
                               '871 Leatherwood Street North Canton, OH 44720',
                               '20 Sage Dr. El Paso, TX 79930', '974 Boston Ave. Huntington, NY 11743',
                               '937 Bohemia Ave. Port Washington, NY 11050',
                               '952 West Oxford Rd. Enfield, CT 06082',
                               '17 Grove St. Lake Jackson, TX 77566',
                               '35 Brookside Drive South Richmond Hill, NY 11419']]
        array[6] = ['Gender',['Female', 'Female', 'Female', 'Female', 'Male', 'Female', 'Male', 'Male', 'Female', 'Female']]
        array[7] = ['Age',[62, 45, 63, 65, 59, 47, 58, 60, 56, 60]]
        array[8] = ['MaritalStatus',['Unmarried', 'Unmarried', 'Married', 'Unmarried', 'Married', 'Unmarried', 'Married', 'Married', 'Unmarried', 'Unmarried']]
        array[9] = ['Interests',['Body Building,Illusion,Protesting,Taxidermy,TV watching,Cartooning,Skateboarding',
                                 'String Figures,Working on cars,Button Collecting,Surf Fishing', 'Saltwater Aquariums',
                                 'Golf,Weather Watcher,Fencing,Leathercrafting,Tutoring Children,Blogging,Building Dollhouses',
                                 'Collecting Antiques,Hula Hooping,Airbrushing', 'Diecast Collectibles,Tool Collecting,Stamp Collecting',
                                 'Beatboxing,Amateur Radio,Survival,Casino Gambling,BMX,Bicycling',
                                 'Papermache,Making Model Cars,Go Kart Racing,Beachcombing,Kayaking,Sleeping,Conworlding',
                                 'Floral Arrangements,Making Model Cars,Slot Car Racing',
                                 'Aircraft Spotting,Pottery,Airsofting,Belly Dancing,Kayaking']]
        return array
    }

    @Test(description = "Checks if values executing by key", dataProvider = "Data for extracted values")
    void valuesForProvidedKeyTest(key, valuesArray){
        ArrayList<String> listOfValues = DataExtractor.valuesForProvidedKey(responseObj.hits.hits._source, key)
        Assert.assertEquals(listOfValues, valuesArray, "Method valuesForProvidedKey returned incorrect data")
    }

    @DataProvider(name = "Data for extracted objects")
    Object[][] dataForExtractedObjects(){
        def array = new Object[3][]
        array[0] = ['_source',1,'{FirstName=JENNEFER, LastName=WENIG, Designation=President, Salary=110000, DateOfJoining=2013-02-07, Address=16 Manor Station Court Huntsville, AL 35803, Gender=Female, Age=45, MaritalStatus=Unmarried, Interests=String Figures,Working on cars,Button Collecting,Surf Fishing}']
        array[1] = ['_source',2,'{FirstName=LAUREN, LastName=RIDENS, Designation=President, Salary=123000, DateOfJoining=2010-01-14, Address=287 SE. Schoolhouse Street Clifton, NJ 07011, Gender=Female, Age=63, MaritalStatus=Married, Interests=Saltwater Aquariums}']
        array[2] = ['_source',3,'{FirstName=VANIA, LastName=HAMMING, Designation=President, Salary=127000, DateOfJoining=1970-01-05, Address=871 Leatherwood Street North Canton, OH 44720, Gender=Female, Age=65, MaritalStatus=Unmarried, Interests=Golf,Weather Watcher,Fencing,Leathercrafting,Tutoring Children,Blogging,Building Dollhouses}']
        return array
    }

    @Test(description = "Checks if objects executing by key and serial number", dataProvider = "Data for extracted objects")
    void objectWhereKeyAppearsTest(key, serialNumber, objectsArray){
        String listOfObjects = DataExtractor.objectsWhereKeyAppears(responseObj.hits.hits, key,serialNumber)
        Assert.assertEquals(listOfObjects,objectsArray , "Method objectsWhereKeyAppears returned incorrect data")
    }
}
