const heading = document.querySelector('#modal-title');

class Page extends CRUDPage
{
    constructor(btnAdd, table, form)
    { super('/api/ticket/ticket-component', btnAdd, table, form); }

    init()
    {
        super.init();
        this.btnAdd.onclick = () => {
            this.form.reset();
            this.form.onsubmit(this.create.bind(this))
            
            heading.textContent = 'Insertion de Pièce à Réparer';
        };
    }

    render(data, page)
    {
        const tbody = table.querySelector('tbody');
        tbody.replaceChildren();

        for(const row of data.page)
        {
            tbody.append(tag('tr', {}, [
                tag('td', {}, [
                    tag('div', {'class': 'd-flex gap-1'}, [
                        BtnUpdate(() => {
                            heading.textContent = 'Modification de Pièce à Réparer';
                            this.form.load({
                                'idTicket': row.idTicket,
                                'idComponent': row.idComponent,
                                'quantity': row.quantity,
                                'costTotal': row.costTotal,
                            });
                            this.form.onsubmit(this.update.bind(this, row.id));
                        }),
                        BtnDelete(this.delete.bind(this, row.id)),
                    ])
                ]),
                tag('td', {}, [text(row.modelCategory)]),
                tag('td', {}, [text(row.serialNumber)]),
                tag('td', {}, [text(row.dateStart)]),
                tag('td', {}, [text(row.componentCategory)]),
                tag('td', {}, [text(row.quantity)]),
                tag('td', {}, [text(row.costTotal)]),
            ]));
        }
        
        const pagination = new Pagination(
            document.querySelector('.pagination'),
            PaginationPage(),
            data.count,
            this.read.bind(this)
        );
        pagination.init();
        pagination.setActive(page);
    }
}

window.addEventListener('load', e => {
    new Page(
        document.getElementById('btn-add'),
        document.getElementById('table'),
        document.getElementById('form')
    ).init()
})