package nl.groothandel.service.factory;

import nl.groothandel.service.domain.DataService;

public interface DataServiceFactory {

    DataService get(String name);

}
