const heading = document.querySelector('#modal-title');

class Page extends CRUDPage
{
    constructor(btnAdd, table, form, formFilter)
    {
        super('/api/ticket/ticket', btnAdd, table, form);
        this.formFilter = new FormHandler(formFilter);
        this.idComponentCategory = '';
    }

    init()
    {
        super.init();

        this.formFilter.onsubmit(e => {
            this.idComponentCategory = this.formFilter.formData().get("idComponentCategory");
            this.read(1);
        });
        this.btnAdd.onclick = () => {
            this.form.reset();
            this.form.onsubmit(this.create.bind(this))
            
            heading.textContent = 'Insertion de Tiquet de Réparation';
        };
    }

    read(page)
    {
        fetch(`${this.apiPath}?page=${page}&idComponentCategory=${this.idComponentCategory}`)
        .then(async (response) => {
            if(response.status === 200)
            { this.render((await response.json()).data, page); }
            else
            { alert('Une erreur est survenue. Veuillez réessayer ultérieurement'); }
        })
        .catch(this.error.bind(this));
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
                            heading.textContent = 'Modification de Tiquet de Réparation';
                            this.form.load({
                                'idCustomer': row.idCustomer,
                                'idModel': row.idModel,
                                'idEngineer': row.idEngineer,
                                'dateStart': row.dateStart,
                                'diagnostic': row.diagnostic,
                                'priceReparation': row.priceReparation,
                            });
                            this.form.onsubmit(this.update.bind(this, row.id));
                        }),
                        BtnDelete(this.delete.bind(this, row.id)),
                    ])
                ]),
                tag('td', {}, [text(row.modelCategory)]),
                tag('td', {}, [text(row.serialNumber)]),
                tag('td', {}, [text(row.brand)]),
                tag('td', {}, [text(row.components)]),
                tag('td', {}, [text(row.ticketState)]),
                tag('td', {align: 'right'}, [text(row.priceReparation)]),
                tag('td', {}, [text(row.dateStart)]),
                tag('td', {}, [text(row.dateEnd)]),
                tag('td', {}, [text(row.name)]),
                tag('td', {}, [text(row.contact || row.email || row.address)]),
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
        document.getElementById('form'),
        document.getElementById('form-filter')
    ).init()
})