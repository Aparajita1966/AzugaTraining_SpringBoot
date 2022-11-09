package com.training.Azuga;

public class Main {

    public static void main(String[] args) {
        MuseumController museumController = new MuseumController();
        museumController.getObjectDetailsWhereTagsAreMoreThanOne();
        museumController.getObjectDetailsForConstituents(2);
        museumController.getObjectsDetailsByJoin(1);
        //museumController.getObjectsDetailsWithoutUsingJoin();
        museumController.getObjectsDetailsUsingAND();
        museumController.getObjectsDetailsUsingAscDesc();
        museumController.getObjectsDetailsUsingMinMax();
        museumController.getObjectsDetailsUsingNotBetween();
        museumController.getObjectsDetailsUsingCount();
        museumController.getObjectsDetailsUsingLeftJoin();
        museumController.getObjectsDetailsUsingExists();
    }
}
