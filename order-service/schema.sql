CREATE TABLE IF NOT EXISTS public.items (
	id uuid NOT NULL,
	"name" varchar(50) NOT NULL,
	info varchar(400) NOT NULL,
    CONSTRAINT items_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.order_items (
	ref_order uuid NOT NULL,
	ref_item uuid NOT NULL
);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE OR REPLACE FUNCTION generate_searchable( _name VARCHAR, _info VARCHAR )
	RETURNS text AS $$
	BEGIN
		RETURN _name || _info;
	END;
$$ LANGUAGE plpgsql immutable;

CREATE TABLE IF NOT EXISTS public.orders (
	id uuid NOT NULL,
	"name" varchar(50) NOT NULL,
	info varchar(400) NOT NULL,
	email varchar(50) NOT NULL,
	dt_register timestamp NOT NULL,
    searchable text NULL GENERATED ALWAYS AS (generate_searchable(name, info)) STORED,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

CREATE INDEX item_idx ON public.order_items USING btree (ref_item);
CREATE INDEX order_idx ON public.order_items USING btree (ref_order);

ALTER TABLE public.order_items ADD CONSTRAINT fk_order_items_ref_item__id FOREIGN KEY (ref_item) REFERENCES public.items(id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE public.order_items ADD CONSTRAINT fk_order_items_ref_order__id FOREIGN KEY (ref_order) REFERENCES public.orders(id) ON DELETE RESTRICT ON UPDATE RESTRICT;

CREATE INDEX idx_orders_searchable ON public.orders USING gist (searchable gist_trgm_ops);

