package org.example.c9laboration2;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.c9laboration2.service.Warehouse;

import static org.example.c9laboration2.entities.PopulateWarehouse.populateWarehouse;

@ApplicationPath("/api")
public class WarehouseApplication extends Application {

}