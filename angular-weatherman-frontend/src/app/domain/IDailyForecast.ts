import { IHourlyForecast } from "./IHourlyForecast";


export interface IDailyForecast {
    dateTime: string;

    minimumTemperatureCelsius: number;
    maximumTemperatureCelsius: number;

    maxWindKph: number;

    precipitation: number;

    conditions: string;

    hourlyForecastList: IHourlyForecast[];
}
