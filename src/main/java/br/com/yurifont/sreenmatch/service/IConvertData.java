package br.com.yurifont.sreenmatch.service;

public interface IConvertData {
    <T> T convertData(String json, Class<T> _class);
}
