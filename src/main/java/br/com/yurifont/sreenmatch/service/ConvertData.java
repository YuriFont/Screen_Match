package br.com.yurifont.sreenmatch.service;

import br.com.yurifont.sreenmatch.model.SeriesData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T convertData(String json, Class<T> _class) {
        try {
            return mapper.readValue(json, _class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
