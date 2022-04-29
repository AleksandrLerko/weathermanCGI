export interface IHourlyForecast {
    dateTime: string;

    hourTemperatureCelsius: number;

    windKph: number;

    precipitation: number;

    conditions: string;
}
