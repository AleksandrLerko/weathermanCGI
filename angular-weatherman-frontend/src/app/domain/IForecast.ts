import { ICurrentForecast } from "./ICurrentForecast";
import { IDailyForecast } from "./IDailyForecast";
import { ILocation } from "./ILocation";


export interface IForecast {
    location: ILocation;
    currentForecast: ICurrentForecast;
    dailyForecast: IDailyForecast[];
}
