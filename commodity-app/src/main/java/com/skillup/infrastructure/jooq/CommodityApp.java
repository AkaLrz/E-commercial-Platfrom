/*
 * This file is generated by jOOQ.
 */
package com.skillup.infrastructure.jooq;


import com.skillup.infrastructure.jooq.tables.Commodity;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommodityApp extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>commodity-app</code>
     */
    public static final CommodityApp COMMODITY_APP = new CommodityApp();

    /**
     * The table <code>commodity-app.commodity</code>.
     */
    public final Commodity COMMODITY = Commodity.COMMODITY;

    /**
     * No further instances allowed
     */
    private CommodityApp() {
        super("commodity-app", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Commodity.COMMODITY);
    }
}