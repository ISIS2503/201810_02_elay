import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'filter'
})
export class FilterPipe implements PipeTransform {
    transform(alarmas: any, term: any): any {
        //check if search term is undefined 
        if (term === undefined) return alarmas;
        //return updated alarmas
        let result = alarmas.filter(function (alarma) {
            return alarma.id.toLowerCase().includes(term);
        });

        if (result.length === 0)
            result = [-1];

        return result;
    }
}


