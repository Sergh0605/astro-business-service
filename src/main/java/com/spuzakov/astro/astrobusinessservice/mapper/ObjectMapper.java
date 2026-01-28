package com.spuzakov.astro.astrobusinessservice.mapper;

import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Интерфейс для преобразования (маппинга) между сущностями (Entity) и DTO.
 * Предоставляет методы для двунаправленного преобразования объектов,
 * а также для обновления DTO из других DTO.
 *
 * @param <E> тип сущности (Entity)
 * @param <D> тип DTO (Data Transfer Object)
 */
public interface ObjectMapper<E, D> {

    /**
     * Преобразует DTO в сущность.
     *
     * @param source исходный DTO объект
     * @return преобразованная сущность
     */
    E mapToEntity(D source);

    /**
     * Преобразует список DTO в список сущностей.
     *
     * @param source список DTO
     * @return список сущностей
     */
    List<E> mapToEntity(List<D> source);

    /**
     * Преобразует сущность в DTO.
     *
     * @param source исходная сущность
     * @return преобразованный DTO
     */
    D mapToDto(E source);

    /**
     * Преобразует список сущностей в список DTO.
     *
     * @param source список сущностей
     * @return список DTO
     */
    List<D> mapToDto(List<E> source);

    /**
     * Обновляет целевой DTO данными из исходного DTO.
     * <p>Используется для частичного обновления данных без создания нового объекта.
     *
     * @param sourceDto исходный DTO с новыми данными
     * @param targetDto целевой DTO для обновления
     */
    void update(D sourceDto, @MappingTarget D targetDto);
}
